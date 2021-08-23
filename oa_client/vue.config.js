module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081/oa/',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
};