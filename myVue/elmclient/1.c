// export default {
//     name: 'BusinessInfo',
//     data() {
//         return {
//             businessId: this.$route.query.businessId,
//             business: {},
//             foodArr: [],
//             user: {}
//         }
//     },
//     created() {
//         this.user = this.$getSessionStorage('user');
//         //根据businessId查询商家信息 
//         this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
//         businessId: this.businessId
//     })).then(response => {
//         this.business = response.data;
//     }).catch(error => {
//         console.error(error);
//     });
//     //根据businessId查询所属食品信息 
//     this.$axios.post('FoodController/listFoodByBusinessId', this.$qs.stringify({
//     businessId: this.businessId
// })).then(response => {
//     this.foodArr = response.data;
//     for (let i = 0; i < this.foodArr.length; i++) {
//         this.foodArr[i].quantity = 0;
//     }
//     //如果已登录，那么需要去查询购物车中是否已经选购了某个食品 
//     if (this.user != null) {
//     this.listCart();
// }
//     }).catch(error => {
//     console.error(error);
// }); },
// methods: {
//     listCart() {
//         this.$axios.post('CartController/listCart', this.$qs.stringify({
//             businessId: this.businessId,
//             userId: this.user.userId
//         })).then(response => {
//             let cartArr = response.data; //遍历所有食品列表
//             for (let foodItem of this.foodArr) {
//                 foodItem.quantity = 0;
//                 for (let cartItem of cartArr) {
//                     if (cartItem.foodId == foodItem.foodId) {
//                         foodItem.quantity = cartItem.quantity;
//                     }
//                 }
//             }
//             this.foodArr.sort();
//         }).catch(error => {
//             console.error(error);
//         });
//     },
//     add(index) {
//         //首先做登录验证
//         if (this.user == null) {
//             this.$router.push({
//                 path: '/login'
//             });
//             return;
//         }
//         if (this.foodArr[index].quantity == 0) { //做insert
//             this.savaCart(index);
//         } else {
//             //做update
//             this.updateCart(index, 1);
//         }
//     },
//     minus(index) {
//         //首先做登录验证
//         if (this.user == null) {
//             this.$router.push({
//                 path: '/login'
//             });
//             return;
//         }
//         if (this.foodArr[index].quantity > 1) { //做update
//             this.updateCart(index, -1);
//         } else {
//             //做delete
//             this.removeCart(index);
//         }
//     },
//     savaCart(index) {
//         this.$axios.post('CartController/saveCart', this.$qs.stringify({
//             businessId: this.businessId,
//             userId: this.user.userId,
//             foodId: this.foodArr[index].foodId
//         })).then(response => {
//             if (response.data == 1) {
//                 //此食品数量要更新为1; this.foodArr[index].quantity = 1; this.foodArr.sort();
//             } else {
//                 alert('向购物车中添加食品失败!');
//             }
//         }).catch(error => {
//             console.error(error);
//         });
//     },
//     updateCart(index, num) {
//         this.$axios.post('CartController/updateCart', this.$qs.stringify({
//             businessId: this.businessId,
//             userId: this.user.userId,
//             foodId: this.foodArr[index].foodId,
//             quantity: this.foodArr[index].quantity + num
//         })).then(response => {
//             if (response.data == 1) {
//                 //此食品数量要更新为1或-1; this.foodArr[index].quantity += num; this.foodArr.sort();
//             } else {
//                 alert('向购物车中更新食品失败!');
//             }
//         }).catch(error => {
//             console.error(error);
//         });
//     },
//     removeCart(index) {
//         this.$axios.post('CartController/removeCart', this.$qs.stringify({
//             businessId: this.businessId,
//             userId: this.user.userId,
//             foodId: this.foodArr[index].foodId
//         })).then(response => {
//             if (response.data == 1) {
//                 //此食品数量要更新为0;视图的减号和数量要消失 this.foodArr[index].quantity = 0; this.foodArr.sort();
//             } else {
//                 alert('从购物车中删除食品失败!');
//             }
//         }).catch(error => {
//             console.error(error);
//         });
//     },
//     toOrder() {
//         this.$router.push({
//             path: '/orders',
//             query: {
//                 businessId: this.business.businessId
//             }
//         });
//     }
// },
// computed: {
//     //食品总价格 
//     totalPrice() {
//     let total = 0;
//     for (let item of this.foodArr) {
//         total += item.foodPrice * item.quantity;
//     }
//     return total;
// },
// //食品总数量 
// totalQuantity() {
// let quantity = 0;
// for (let item of this.foodArr) {
//     quantity += item.quantity;
// }
// return quantity;
//     },
// //结算总价格 
// totalSettle() {
// return this.totalPrice + this.business.deliveryPrice;
//             }
// } }



// import { ref, onMounted } from 'vue';
// import axios from 'axios'; // 添加这一行来引入 axios
// import qs from 'qs';
// import { useRoute, useRouter } from 'vue-router';

// export default {
//     name: 'BusinessInfo',
//     setup() {
//         const route = useRoute();
//         const businessId = ref(route.query.businessId);
//         const business = ref({});
//         const foodArr = ref([]);
//         const user = ref(null);

//         const loadBusinessInfo = async () => {
//             try {
//                 const responseBusiness = await axios.post('BusinessController/getBusinessById', qs.stringify({
//                     businessId: businessId.value
//                 }));
//                 business.value = responseBusiness.data;

//                 const responseFood = await axios.post('FoodController/listFoodByBusinessId', qs.stringify({
//                     businessId: businessId.value
//                 }));
//                 foodArr.value = responseFood.data;
//                 for (let i = 0; i < foodArr.value.length; i++) {
//                     foodArr.value[i].quantity = 0;
//                 }

//                 if (user.value != null) {
//                     loadCart();
//                 }
//             } catch (error) {
//                 console.error(error);
//             }
//         };

//         const loadCart = async () => {
//             try {
//                 const responseCart = await axios.post('CartController/listCart', qs.stringify({
//                     businessId: businessId.value,
//                     userId: user.value.userId
//                 }));
//                 const cartArr = responseCart.data;

//                 for (let foodItem of foodArr.value) {
//                     foodItem.quantity = 0;
//                     for (let cartItem of cartArr) {
//                         if (cartItem.foodId === foodItem.foodId) {
//                             foodItem.quantity = cartItem.quantity;
//                         }
//                     }
//                 }
//                 foodArr.value.sort();
//             } catch (error) {
//                 console.error(error);
//             }
//         };

//         onMounted(() => {
//             user.value = JSON.parse(sessionStorage.getItem('user'));
//             loadBusinessInfo();
//         });

//         // Other methods...

//         return {
//             businessId,
//             business,
//             foodArr,
//             user,
//             // Other data and methods...
//         };
//     },
//     computed: {
//         totalPrice() {
//             let total = 0;
//             for (let item of this.foodArr) {
//                 total += item.foodPrice * item.quantity;
//             }
//             return total;
//         },
//         totalQuantity() {
//             let quantity = 0;
//             for (let item of this.foodArr) {
//                 quantity += item.quantity;
//             }
//             return quantity;
//         },
//         totalSettle() {
//             return this.totalPrice + this.business.deliveryPrice;
//         }
//     }
// };