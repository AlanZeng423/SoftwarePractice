// import { fileURLToPath, URL } from 'node:url'

// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'

// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [
//     vue(),
//   ],
//   resolve: {
//     alias: {
//       '@': fileURLToPath(new URL('./src', import.meta.url))
//     }
//   }
// })

module.exports = {
  devServer: {
    port: 8081 
  }
}




// module.exports = {
//   devServer: {
//     proxy: {
//       '/elm': {
//         target: 'http://localhost:8080/', // 后端服务器地址
//         changeOrigin: true,
//         pathRewrite: { '^/elm': '' }
//       }
//     }
//   }
// };
