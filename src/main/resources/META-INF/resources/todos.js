(() => {
  const application = Stimulus.Application.start();

  application.register("todo", class extends Stimulus.Controller {
    static targets = [ "item", "edit" ];
    showEdit() {
      this.itemTarget.classList.add("editing");
      this.editTarget.focus();
      this.editTarget.selectionStart = this.editTarget.value.length;
    }
    hideEdit() {
      this.itemTarget.classList.remove("editing");
    }
  });
})();
