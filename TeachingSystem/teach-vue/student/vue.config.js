const {defineConfig} = require("@vue/cli-service");
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 8000,
    },
    assetsDir: "static",
    parallel: false,
    publicPath: "./",
});
