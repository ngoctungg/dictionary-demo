let toggler = document.getElementsByClassName("caret");
for (i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener("click", function () {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
    });
}
//---set up quill
const options = {
    readOnly: true,
};
const quill = new Quill('#quill-content', options);

let btnPosts = document.querySelectorAll("[name=btn_post]");

Array.from(btnPosts).forEach(el=>{
    el.addEventListener("click",handleClickBtnPost);
    let id = getPostIdFromURl();
    if (el.id.split("_")[1] === id){
        el.click();
        el.parentElement.previousElementSibling.click();
    }
})
async function handleClickBtnPost(e){
    let id = e.target.id.split("_")[1];
    let url = window.location.origin;
    loading.classList.toggle("d-none");
    let post = await sendRequest(`${url}/api/posts/${id}`, "Get");
    loading.classList.toggle("d-none");
    if(post.status =="0" || post.status === "404" ){
        showToast(post.msg,true);
    }else{
        quill.setContents(JSON.parse(post.content),"api");
        Array.from(btnPosts).forEach(el=>{
            el.disabled  =false;
            el.classList.remove("text-info");
        });
        e.target.disabled = true;
        e.target.classList.add("text-info");
        e.target.classList.remove("text-dark");

        window.history.pushState("",post.title,window.location.origin+"/post/"+post.id);
        document.title = post.title;
    }
}

function getPostIdFromURl(){
    let paths = window.location.pathname.split("/");
    if(paths.length < 3){
        return null;
    }
    return paths[2];
}