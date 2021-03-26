console.log("Hello");

async function signIn() {
    const userName = document.getElementById('username').value;
    const userEmail = document.getElementById('useremail').value;
    const password = document.getElementById('password').value;
    let user = {
        name: userName,
        email: userEmail,
        password: password,
    };

    let json = JSON.stringify(user);
    await fetch('http://localhost:8080/registration', {
        method: 'POST'/*,
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: json*/
    });
    console.log(json);
}
