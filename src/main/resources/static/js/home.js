let toggler = document.getElementsByClassName("caret");
for (let i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener("click", function () {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
    });
}
//---set up quill
Quill.register({
    'modules/better-table': quillBetterTable
}, true)
const quill = new Quill('#quill-content', {
    readOnly: true, modules: {
        syntax: true,
    },
});
document.addEventListener("DOMContentLoaded", function () {

    let btnPosts = document.querySelectorAll("[name=btn_post]");
    let lkEdit = document.getElementById("lk_edit");
    if (lkEdit) lkEdit.addEventListener("click", handleClicklkEdit);

    Array.from(btnPosts).forEach(el => {
        el.addEventListener("click", handleClickBtnPost);
    });
    Array.from(document.getElementsByClassName("ql-clipboard")).forEach(el => el.classList.toggle("d-none"));
    loadPostById();

    function loadPostById() {
        let id = getPostIdFromURl();
        if (id == null) return;
        let el = document.getElementById("p_" + id);
        if (el === null) {
            quill.setContents(JSON.parse("{\"ops\":[{\"insert\":\"Post is not found\"},{\"attributes\":{\"header\":2},\"insert\":\"\\n\"}]}"));
            return;
        }
        if (+el.id.split("_")[1] === id) {
            el.click();
            el.parentElement.previousElementSibling.click();
        }
    }

    function handleClicklkEdit(e) {
        if (getPostIdFromURl() === null) {
            e.preventDefault();

        }
    }

    async function handleClickBtnPost(e) {
        e.preventDefault();
        let id = e.target.id.split("_")[1];
        let url = window.location.origin;
        let res = await sendRequest(`${url}/api/posts/${id}`, "Get");
        if (res.status === "0" || res.status === "404") {
            showToast(res.msg, true);
        } else {
            quill.setContents(JSON.parse(res.data.content), "api");
            Array.from(document.getElementsByClassName('ql-syntax')).forEach((block) => {
                hljs.highlightBlock(block);
            });
            Array.from(btnPosts).forEach(el => {
                el.disabled = false;
                el.classList.remove("text-info");
            });
            e.target.disabled = true;
            e.target.classList.add("text-info");
            e.target.classList.remove("text-dark");
            renderFileLabel(res.data.files,true);
            document.getElementById("title").innerText = res.data.title;
            document.getElementById("user").innerText = res.data.createdBy.account;
            document.getElementById("time").innerText = (new Date(res.data.createdAt)).toDateString();

            window.history.pushState("", res.title, "/post/" + res.data.id);
            document.title = res.data.title;
            if (lkEdit) lkEdit.href = "/edit/" + res.data.id;
        }
    }

    //render all label file in list of file,
    //TODO: optimize render
    function renderFileLabel(files, isClean = false) {
        let fileLabelContainer = document.getElementById('file_label');
        if (isClean) fileLabelContainer.innerHTML = "";
        let id = 0;
        for (const file of files) {
            const htmlEl =
                `<span class="form-file-text" ${id !== (files.length - 1)?'style="border-top: 0px"':""}>
                        ${file.name}   
                        <button aria-label="Download" data-id="${file.id ? file.id : ''}" 
                        data-name="btn_file_down_${id++}"
                            class="btn float-right" type="button">
                            <svg  width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-download" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                              <path fill-rule="evenodd" d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                              <path fill-rule="evenodd" d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
                            </svg>
                        </button>
                      </span>`;
            fileLabelContainer.insertAdjacentHTML("afterbegin", htmlEl);
        }
        document.querySelectorAll("[data-name^='btn_file_down']")
            .forEach(el => el.addEventListener("click", async e=>{
                let postId = getPostIdFromURl();
                let fileId = "";
                if(e.currentTarget.dataset.id){
                    fileId = e.currentTarget.dataset.id;
                }
                let url = `${window.location.origin}/api/posts/${postId}/files/${fileId}`;
                fetch(url).then(res=>{
                    if(!res.ok){
                        showToast("Can't download file",true);
                    }else {
                        window.location.href = url;
                    }
                }).catch(error => {
                    showToast("Can't download file",true);
                });
            }));
    }
});


