//set up quill
const toolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    ['blockquote', 'code-block'],

    [{'header': 1}, {'header': 2}],               // custom button values
    [{'list': 'ordered'}, {'list': 'bullet'}],
    [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
    [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
    [{'direction': 'rtl'}],                         // text direction

    [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
    [{'header': [1, 2, 3, 4, 5, 6, false]}],

    [{'color': []}, {'background': []}],          // dropdown with defaults from theme
    [{'font': []}],
    [{'align': []}],

    ['clean'],                                       // remove formatting button
    ['link', 'image']
];
const options = {
    // debug: 'info',
    modules: {
        toolbar: toolbarOptions
    },
    placeholder: 'Compose an epic...',
    readOnly: false,
    theme: 'snow'
};
const editor = new Quill('#quill-content', options);
//--------------------------------------------
let cbNewCategory = document.getElementById("new_category");
let inputCategory = document.getElementById("input_category");
let selectionCategory = document.getElementById("selection_category");
let inputTitle = document.getElementById("title");
let inputTag = document.getElementById("tag");
let inputSummary = document.getElementById("summary");

cbNewCategory.addEventListener("change", handleEventCbNewCategory);
function handleEventCbNewCategory(e) {
    if (cbNewCategory.checked === true) {
        inputCategory.parentElement.classList.remove("d-none");
        selectionCategory.parentElement.classList.add("d-none");
    } else {
        inputCategory.parentElement.classList.add("d-none");
        selectionCategory.parentElement.classList.remove("d-none");
    }
}

if (selectionCategory.options.length === 0) {
    cbNewCategory.click();
    cbNewCategory.setAttribute("disabled", true);
}
//handle btn---------------------------------------------
let btnDelete = document.getElementById("btn_delete");
let btnSave = document.getElementById("btn_save");
btnDelete.addEventListener("click", handleDeleteEvent);
btnSave.addEventListener("click", handleSaveEvent);

document.addEventListener("DOMContentLoaded", async function(event) {
    await loadPostById();
    if(!getPostIdFromURl()){
        btnDelete.remove();
    }else{
        btnDelete.addEventListener("click",handleDeleteEvent);
    }

});

async function handleDeleteEvent(e) {
    let sessionData = JSON.parse(window.sessionStorage.getItem("pre_data"));
    let id = sessionData && sessionData.id ;
    let post = await sendRequest(`${window.location.origin}/api/posts/${id}`, "Delete");
    showToast(post.msg, post.status === "0" || post.status === "404");
    if (post.status === "200"){
        window.location = "/post";
    }
}

async function handleSaveEvent(e) {
    const categoryName = cbNewCategory.checked ? inputCategory.value :
        selectionCategory.options[selectionCategory.selectedIndex].text
    const categoryId = cbNewCategory.checked ? undefined :
        selectionCategory.options[selectionCategory.selectedIndex].value;
    const title = inputTitle.value;
    const tag = inputTag.value;
    const summary = inputSummary.value;
    const content = JSON.stringify(editor.getContents());
    const requiredFieldName = ["Category","Title","Tag", "Summary", "Content"];
    const requiredField = ["categoryName","title","tag", "summary", "content"];
    const data = {categoryName,categoryId,title,tag,summary,content};
    for (let i = 0; i < requiredField.length; i++) {
        const field = requiredField[i];
        let msg = null;
        if (data[field] === null || data[field].length < 2  ){
            msg = requiredFieldName[i];
        }
        if(field === "content" && editor.getLength() < 20){
            msg = requiredFieldName[i];
        }
        if(msg !== null){
            showToast(`${msg} is required !!!`,true);
            return;
        }
    }
    let url = window.location.origin;
    let sessionData = JSON.parse(window.sessionStorage.getItem("pre_data"));
    let preId = sessionData && sessionData.id ;
    let msg = "";
    if(getPostIdFromURl() === preId){
        data["id"] = preId;
        msg = await sendRequest(`${url}/api/posts`, "Put", data);
    }else{
        msg = await sendRequest(`${url}/api/posts`, "Post", data);
    }
    showToast(msg.msg, msg.status === "0");
}

async function loadPostById(){
    let id = getPostIdFromURl();
    if(id == null) return ;
    let url = window.location.origin;
    let post = await sendRequest(`${url}/api/posts/${id}`, "Get");
    if (post.status === "0" || post.status === "404"){
        showToast(post.msg, true);
        btnDelete.remove();
        return;
    }
    window.sessionStorage.setItem("pre_data",JSON.stringify(post.data));
    inputTitle.value = post.data.title;
    inputTag.value = post.data.title;
    inputSummary.value = post.data.summary;
    selectionCategory.value = post.data.category.id;
    editor.setContents(JSON.parse(post.data.content));
}


