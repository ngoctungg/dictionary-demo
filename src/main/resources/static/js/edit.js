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
var toastElList = [].slice.call(document.querySelectorAll('.toast'));
var toastList = toastElList.map(function (toastEl) {
    return new bootstrap.Toast(toastEl);
});
toastList.forEach(el => {
    el._element.addEventListener("hidden.bs.toast", () => {
        el._element.classList.remove("bg-danger");
        el._element.classList.remove("bg-success");
        el._element.classList.add("click-through");
    })
})

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

function handleDeleteEvent(e) {
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
    let msg = await sendRequest(`${url}/api/posts`, "Post", data);
    showToast(msg.msg, msg.status === undefined);
}

function showToast(msg = "", isError = false) {
    toastList.forEach(el => {
        el._element.getElementsByClassName("toast-body")[0].innerHTML = msg;
        el._element.classList.remove("click-through");
        el._element.classList.add(isError ? "bg-danger" : "bg-success");
        el.show();
    });
}

async function sendRequest(url = "", method = "Get", data = {}) {
    try {
        const response = await fetch(url, {
            method: method,
            mode: "cors",
            cache: "no-cache",
            credentials: "same-origin",
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response.json();
    } catch (e) {
        return {"msg": "Your action was unsuccessful"};
    }
}
