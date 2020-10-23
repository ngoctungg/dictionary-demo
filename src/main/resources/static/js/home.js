let toggler = document.getElementsByClassName("caret");
for (i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener("click", function () {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
    });
}
//---set up quill
const quill = new Quill('#quill-content', { readOnly: true,});
let btnPosts = document.querySelectorAll("[name=btn_post]");
let lkEdit = document.getElementById("lk_edit");
document.addEventListener("DOMContentLoaded", function(event) {
    lkEdit.addEventListener("click",handleClicklkEdit);
    Array.from(btnPosts).forEach(el=>{
        el.addEventListener("click",handleClickBtnPost);
    });
    Array.from(document.getElementsByClassName("ql-clipboard")).forEach(el=>el.classList.toggle("d-none"));
    loadPostById();
});
function loadPostById(){
    let id = getPostIdFromURl();
    if(id == null) return ;
    let el = document.getElementById("p_"+id);
    if(el === null){
        quill.setContents(JSON.parse("{\"ops\":[{\"insert\":\"Post is not found\"},{\"attributes\":{\"header\":2},\"insert\":\"\\n\"}]}"));
        return ;
    }
    if (el.id.split("_")[1] === id){
        el.click();
        el.parentElement.previousElementSibling.click();
    }
}
function handleClicklkEdit(e){
    if (getPostIdFromURl() === null){
        e.preventDefault();
        return ;
    }
}
async function handleClickBtnPost(e){
    let id = e.target.id.split("_")[1];
    let url = window.location.origin;
    let post = await sendRequest(`${url}/api/posts/${id}`, "Get");
    if(post.status =="0" || post.status === "404" ){
        showToast(post.msg,true);
    }else{
        quill.setContents(JSON.parse(post.data.content),"api");
        Array.from(btnPosts).forEach(el=>{
            el.disabled  =false;
            el.classList.remove("text-info");
        });
        e.target.disabled = true;
        e.target.classList.add("text-info");
        e.target.classList.remove("text-dark");

        window.history.pushState("",post.title,"/post/"+post.data.id);
        document.title = post.data.title;
        lkEdit.href = "/edit/"+post.data.id;
    }
}
