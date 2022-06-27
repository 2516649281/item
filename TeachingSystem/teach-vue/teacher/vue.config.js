const {defineConfig} = require("@vue/cli-service");
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 8001,
    },
    assetsDir: "static",
    parallel: false,
    publicPath: "./",
});
