const {defineConfig} = require("@vue/cli-service");
module.exports = defineConfig({
    assetsDir: "static",
    parallel: false,
    publicPath: "./",
    transpileDependencies: true,
    devServer: {
        port: 8002,
        historyApiFallback: true,
    },
});
