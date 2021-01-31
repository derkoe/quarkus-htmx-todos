(() => {
  const application = Stimulus.Application.start();

  application.register(
    "todos",
    class extends Stimulus.Controller {
      static targets = ["toggle"];
      toggle() {
        const anyChecked = this.toggleTargets.find((element) => element.checked) !== undefined;
        this.toggleTargets.forEach((element) => (element.checked = !anyChecked));
      }
    }
  );
})();
