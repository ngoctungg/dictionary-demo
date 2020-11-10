async function sendRequest(url = "", method = "Get", data = null) {
    try {
        let options = {
            method: method,
            mode: "cors",
            credentials: "same-origin",
            redirect: 'follow',
            headers:{
                'Content-Type': 'application/json'
            }
        };
        if (["post", "put","delete"].includes(method.toLowerCase()) && data) {
            if (data instanceof FormData) {
                delete options.headers["Content-Type"];
                options["body"] = data;
            }else {
                options["body"] = JSON.stringify(data);
            }
        }
        loading.classList.toggle("d-none");
        const response = await fetch(url, options);
        if (!response.ok) {
            throw Error(response.statusText);
        }
        loading.classList.toggle("d-none");
        return response.json();
    } catch (e) {
        loading.classList.toggle("d-none");
        return {status: "0", "msg": e.msg ? e.msg : "Your action was unsuccessful"};
    }
}

let loading = document.getElementById("loading");
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

function showToast(msg = "", isError = false) {
    toastList.forEach(el => {
        el._element.style.zIndex = 2;
        el._element.getElementsByClassName("toast-body")[0].innerHTML = msg;
        el._element.classList.remove("click-through");
        el._element.classList.add(isError ? "bg-danger" : "bg-success");
        el.show();
    });
}


function getPostIdFromURl() {
    let paths = window.location.pathname.split("/");
    if (paths.length < 3) {
        return null;
    }
    return +paths[2];
}

let sidebarWrapper = document.getElementById("sidebar-wrapper");
if (sidebarWrapper) {
    document.getElementById("btn_cat").addEventListener("click", (e) => {
        if (sidebarWrapper.style.marginLeft === "" || sidebarWrapper.style.marginLeft === "-15rem") {
            sidebarWrapper.style.marginLeft = 0;
        } else {
            sidebarWrapper.style.marginLeft = "-15rem";
        }
    });

    window.addEventListener('resize', (e) => {
        if (window.innerWidth >= 768) {
            sidebarWrapper.style.marginLeft = 0;
        } else {
            sidebarWrapper.style.marginLeft = "-15rem";
        }
    });
}
