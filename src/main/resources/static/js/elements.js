function addCategory() {
    const block = document.getElementById('newCategory');
    const button = document.getElementById('addCat');
    button.style.visibility = 'hidden';
    block.style.visibility = 'visible';

}

function addAccount() {
    const block = document.getElementById('newAccount');
    const button = document.getElementById('addAcc');
    button.style.visibility = 'hidden';
    block.style.visibility = 'visible';

}

function cancelAddingCategory() {
    const block = document.getElementById('newCategory');
    const button = document.getElementById('addCat');
    block.style.visibility = 'hidden';
    button.style.visibility = 'visible';
}

function cancelAddingAccount() {
    const block = document.getElementById('newAccount');
    const button = document.getElementById('addAcc');
    block.style.visibility = 'hidden';
    button.style.visibility = 'visible';
}


function enterCategory() {
    const addBtn = document.getElementById('addCatBtn');
    const text = document.getElementById('textCat');

    addBtn.disabled = false;
    if (text.value == '') {
        addBtn.disabled = true;
    }
};

function enterAmount() {
    const addBtn = document.getElementById('addAccBtn');
    const text1 = document.getElementById('textAcc');
    const text2 = document.getElementById('textCur');
    const text3 = document.getElementById('textAmount');
    addBtn.disabled = false;
    if (text1.value == '' || text2.value == '' || text3.value == '') {
        addBtn.disabled = true;
    }
}

function enterCurrency() {
    const addBtn = document.getElementById('addAccBtn');
    const text1 = document.getElementById('textAcc');
    const text2 = document.getElementById('textCur');
    const text3 = document.getElementById('textAmount');
    addBtn.disabled = false;
    if (text1.value == '' || text2.value == '' || text3.value == '') {
        addBtn.disabled = true;
    }
}

function enterAccount() {
    const addBtn = document.getElementById('addAccBtn');
    const text1 = document.getElementById('textAcc');
    const text2 = document.getElementById('textCur');
    const text3 = document.getElementById('textAmount');
    addBtn.disabled = false;
    if (text1.value == '' || text2.value == '' || text3.value == '') {
        addBtn.disabled = true;
    }
}