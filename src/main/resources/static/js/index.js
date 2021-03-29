var makeElement = function (tagName, className, text) {
    var element = document.createElement(tagName);
    if (className) {
        element.classList.add(className);
    }
    if (text) {
        element.textContent = text;
    }
    if (className == "btn" && text == '\u00D7') {
        element.onclick = removeCard;
    }
    return element;
};

console.log("KEK");