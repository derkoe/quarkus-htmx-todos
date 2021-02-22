(() => {
  const ESCAPE_KEY = 27;

  function showEdit(item) {
    item.classList.add("editing");
    const editField = item.querySelector(".edit");
    editField.focus();
    editField.selectionStart = editField.value.length;
    editField.addEventListener("blur", () => item.classList.remove("editing"));
    editField.addEventListener("keyup", (event) => {
      if (event.keyCode === ESCAPE_KEY) {
        item.classList.remove("editing");
      }
    });
  }

  function addListeners() {
    document.querySelectorAll("#todo-list li").forEach((item) => {
      item.addEventListener("dblclick", () => showEdit(item));
    });
  }

  window.addEventListener("htmx:load", addListeners);
  window.addEventListener("clear-add-todo", () => {
    document.querySelectorAll(".new-todo").forEach((elm) => (elm.value = ""));
  });
})();
