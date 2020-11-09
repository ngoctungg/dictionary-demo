'use strict'
document.addEventListener("DOMContentLoaded", async function (event) {
//set up quill
    Quill.register({
        'modules/better-table': quillBetterTable
    }, true)
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
        ['link', 'image'],
        ['table']
    ];
    const options = {
        // debug: 'info',
        modules: {
            toolbar: toolbarOptions,
            table: false,
            'better-table': {
                operationMenu: {
                    items: {
                        unmergeCells: {
                            text: 'Another unmerge cells name'
                        }
                    }
                }
            },
            keyboard: {
                bindings: quillBetterTable.keyboardBindings
            }
        },
        placeholder: 'Compose an epic...',
        readOnly: false,
        theme: 'snow'
    };
    const editor = new Quill('#quill-content', options);
    editor.getModule('toolbar').addHandler('table', () => {
        editor.getModule("better-table").insertTable(3, 3);
    });
//--------------------------------------------
    let cbNewCategory = document.getElementById("new_category");
    let inputCategory = document.getElementById("input_category");
    let selectionCategory = document.getElementById("selection_category");
    let inputTitle = document.getElementById("title");
    let inputTag = document.getElementById("tag");
    let inputSummary = document.getElementById("summary");
    let btnDelete = document.getElementById("btn_delete");
    let btnSave = document.getElementById("btn_save");
    let btnHome = document.getElementById("btn_home");
    let customFile = document.getElementById('customFile');
    let fileLabelContainer = document.getElementById('file_label');
    const uploadFiles = [];
    const remoteFiles = [];
    const remoteDelFiles = [];

    await init();
    async function init() {

        cbNewCategory.addEventListener("change", handleEventCbNewCategory);
        btnDelete.addEventListener("click", handleDeleteEvent);
        btnSave.addEventListener("click", handleSaveEvent);
        customFile.addEventListener("change",handleChosenFile);
        if (selectionCategory.options.length === 0) {
            cbNewCategory.click();
            cbNewCategory.setAttribute("disabled", true);
        }
        if (!getPostIdFromURl()) {
            btnDelete.remove();
        } else {
            btnDelete.addEventListener("click", handleDeleteEvent);
        }

        await loadPostById();
    }

    function handleChosenFile(e){
        if(customFile.files.length){
            uploadFiles.push(...customFile.files);
        }
        renderFileLabel(remoteFiles,true);
        renderFileLabel(uploadFiles);
    }

    //render all label file in list of file,
    //TODO: optimize render
    function renderFileLabel(files,isClean=false){
        if (isClean) fileLabelContainer.innerHTML= "";
        let id = 0;
        for(const file of files){
            const htmlEl = `<span class="form-file-text borb-0">
                        ${file.name} - ${getFileSize(file.size)}   
                        <button aria-label="Close" data-id="${file.id?file.id:''}" 
                        data-name="btn_file_del_${id++}"
                            class="btn-close float-right" type="button"></button>
                      </span>`;
            fileLabelContainer.insertAdjacentHTML("afterbegin",htmlEl);
        }
        const btn = '<span class="form-file-button" style="margin-left: 0px">Choose file</span>'
        if (isClean) fileLabelContainer.insertAdjacentHTML("beforeend",btn);
        document.querySelectorAll("[data-name^='btn_file_del']")
            .forEach(el=>el.addEventListener("click",handleDeleteFile));
    }

    /**
     * @param{number} size in bytes
     * @return{string}
     */
    function getFileSize(size){
        if(!size) return "";
        let kb = size / 1024;
        if(kb <= 1024){
            return `${kb.toFixed(2)} kB`;
        }else{
            return `${(kb/1024).toFixed(2)} MB`;
        }
    }
    function handleDeleteFile(e){
        let id = e.target.dataset.name.split("_")[3];
        if(!id)  return;
        id = parseInt(id);
        if(e.target.dataset.id){
            let res = remoteFiles.splice(id,1);
            remoteDelFiles.push(...res);
        }else{
            uploadFiles.splice(id,1);
        }
        renderFileLabel(remoteFiles,true);
        renderFileLabel(uploadFiles);

    }
    async function handleDeleteEvent(e) {
        let sessionData = JSON.parse(window.sessionStorage.getItem("pre_data"));
        let id = sessionData && sessionData.id;
        let post = await sendRequest(`${window.location.origin}/api/posts/${id}`, "Delete");
        showToast(post.msg, post.status === "0" || post.status === "404");
        if (post.status === "200") {
            window.location = "/post";
        }
    }

    function handleEventCbNewCategory(e) {
        if (cbNewCategory.checked === true) {
            inputCategory.parentElement.classList.remove("d-none");
            selectionCategory.parentElement.classList.add("d-none");
        } else {
            inputCategory.parentElement.classList.add("d-none");
            selectionCategory.parentElement.classList.remove("d-none");
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
        const requiredFieldName = ["Category", "Title", "Tag", "Summary", "Content"];
        const requiredField = ["categoryName", "title", "tag", "summary", "content"];
        const data = {categoryName, categoryId, title, tag, summary, content};
        for (let i = 0; i < requiredField.length; i++) {
            const field = requiredField[i];
            let msg = null;
            if (data[field] === null || data[field].length < 2) {
                msg = requiredFieldName[i];
            }
            if (field === "content" && editor.getLength() < 20) {
                msg = requiredFieldName[i];
            }
            if (msg !== null) {
                showToast(`${msg} is required !!!`, true);
                return;
            }
        }
        let url = window.location.origin;
        let sessionData = JSON.parse(window.sessionStorage.getItem("pre_data"));
        let preId = sessionData && sessionData.id;
        let res = "";
        if (getPostIdFromURl() && getPostIdFromURl() === preId) {
            data["id"] = preId;
            //TODO:
            res = await sendRequest(`${url}/api/posts`, "Put", data);
        } else {
            res = await sendRequest(`${url}/api/posts`, "Post", data);
            data["id"] = res.data.id;
        }
        if (res.status === "0"){
            showToast(res.msg, res.status === "0");
            return;
        }
        if(uploadFiles.length){
            let formDataFile = new FormData();
            uploadFiles.forEach(file=>formDataFile.append("files",file));
            let uploadFileRes = await sendRequest(`${url}/api/posts/${ data["id"]}/files`,
                "put",
                formDataFile);
            res.status = uploadFileRes.status;
            res.msg += "<br>"+uploadFileRes.msg+"<br>";
        }
        if(remoteDelFiles.length){
            let ids = remoteDelFiles.map(file=>file.id);
            let deleteFileRes = await sendRequest(`${url}/api/posts/${ data["id"]}/files`,
                "delete",
                ids);
            res.status = deleteFileRes.status;
            res.msg += deleteFileRes.msg +"<br>";
        }
        showToast(res.msg, res.status === "0");
    }

    async function loadPostById() {
        let id = getPostIdFromURl();
        if (id == null) return;
        let url = window.location.origin;
        let post = await sendRequest(`${url}/api/posts/${id}`, "Get");
        if (post.status === "0" || post.status === "404") {
            showToast(post.msg, true);
            btnDelete.remove();
            return;
        }
        window.sessionStorage.setItem("pre_data", JSON.stringify(post.data));
        inputTitle.value = post.data.title;
        inputTag.value = post.data.title;
        inputSummary.value = post.data.summary;
        selectionCategory.value = post.data.category.id;
        editor.setContents(JSON.parse(post.data.content));
        if(post.data.files) {
            remoteFiles.push(...post.data.files);
            renderFileLabel(remoteFiles);
        }
        btnHome.innerText = "Back";
        btnHome.setAttribute("href", `/post/${post.data.id}`);
    }

});