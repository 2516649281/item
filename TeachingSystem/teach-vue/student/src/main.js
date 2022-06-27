import {createApp, VueElement} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import installElementPlus from "./plugins/element";
import SIdentify from "./components/identify";

const app = createApp(App);
installElementPlus(app);
app.use(store).use(router).mount("#app");
app.use(SIdentify);
