async function sendRequest(url = "", method = "Get", data = {}) {
    try {
        let options = {
            method: method,
            mode: "cors",
            credentials: "same-origin",
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow'
        };
        if (method.toLowerCase() === "post"){
            options["body"]= JSON.stringify(data);
        }
        const response = await fetch(url, options);
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response.json();
    } catch (e) {
        return {status:"0","msg": "Your action was unsuccessful"};
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
        el._element.getElementsByClassName("toast-body")[0].innerHTML = msg;
        el._element.classList.remove("click-through");
        el._element.classList.add(isError ? "bg-danger" : "bg-success");
        el.show();
    });
}