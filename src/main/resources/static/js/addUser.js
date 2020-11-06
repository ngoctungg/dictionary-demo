'use strict'
document.addEventListener("DOMContentLoaded", async () => {
    let modal = new bootstrap.Modal(document.getElementById('myModal'), {
        keyboard: false,
        show: true
    });
    let accountEle = document.getElementById("account");
    let passwordEle = document.getElementById("password");
    let roleEle = document.getElementById("role");
    let isCreate = undefined;
    let id = undefined;
    document.getElementById('myModal').addEventListener("hidden.bs.modal", () => {
        location.reload();
    });
    document.getElementById("btn_new").addEventListener("click",  () => {
        isCreate = true;
        modal.show();
    });
    document.getElementById("btn_save").addEventListener("click", async () => {
        let data = {};
        let method = "";
        let account = accountEle.value;
        let password = passwordEle.value;
        let role = roleEle.value;
        if (isCreate) {
            if (account.trim().length == 0 || role == 0 || password.trim().length == 0) {
                showToast("Please enter requires fields ", true);
                return;
            }
            data.account = account;
            data.password = password;
            data.role = role;
            method = "post";
        } else {
            if (password && password.trim().length) {
                data.password = password;
            }
            data.role = role;
            data.id = id;
            method = "put";
        }
        let res = await sendRequest(`${window.location.origin}/api/user`, method,data);
        showToast(res.msg, res.status === "0" || res.status === "404");
    });
    document.querySelectorAll("button[id^='btn_deactivate']").forEach((btn) => {
        btn.addEventListener("click", async (e) => {
            let idBtn = btn.id;
            let idRow = idBtn.split("_")[2];
            let res = await sendRequest(`${window.location.origin}/api/user/${idRow}`, "delete");
            showToast(res.msg, res.status === "0" || res.status === "404");
            if(res.status !== "0" && res.status !== "404"){
                location.reload();
            }
        });
    })
    document.querySelectorAll("button[id^='btn_edit']").forEach((btn) => {
        btn.addEventListener("click", async (e) => {
            let idRow = btn.id.split("_")[2];
            id = idRow;
            let row = document.getElementById(`acc_${idRow}`);
            accountEle.value = row.cells[0].innerText;
            passwordEle.value = "";
            roleEle.value = parseInt(Array.from(document.getElementById("role").options)
                .map(el => el.innerText.toLowerCase())
                .indexOf(row.cells[1].innerText.toLowerCase())) + 1;
            accountEle.toggleAttribute("readonly");
            modal.show();
        });
    })
    document.querySelectorAll("button[id^='btn_activate']").forEach((btn) => {
        btn.addEventListener("click", async (e) => {
            let idBtn = btn.id;
            let idRow = idBtn.split("_")[2];
            let res = await sendRequest(`${window.location.origin}/api/user`,
                "put",
                {id: idRow, active: true});
            showToast(res.msg, res.status === "0" || res.status === "404");
            if(res.status !== "0" && res.status !== "404"){
                location.reload();
            }
        });
    })
});