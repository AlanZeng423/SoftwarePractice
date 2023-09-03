<template>
    <div class="wrapper">
        <header>
            我的信息
        </header>
        <div class="profile-header">
            <img class="profile-picture" :src="user.userImg" alt="UserImg" />
        </div>
        
        <div class="profile-details">
            <div class="detail-item">
                <strong>账号：</strong>{{ user.userId }}
            </div>
            <div class="detail-item">
                <strong>昵称：</strong>{{ user.userName }}
            </div>
            <div class="detail-item">
                <strong>性别：</strong>{{ convertGenderText(user.userSex) }}
            </div>
            <div class="detail-item">
                <strong>积分：</strong>{{ point }}
            </div>


        </div>
        <div class="container">
            <button class="logout-Button" @click="logout">退出登录</button>
        </div>
        
        <Footer></Footer>
    </div>
</template>


<script>
import { ref, onMounted, inject } from 'vue';
import Footer from '../components/Footer.vue';
// const $getSessionStorage = inject('$getSessionStorage');
import axios from 'axios';
import qs from 'qs';
import { useRouter } from 'vue-router';
export default {
    name: 'UserInfo',
    components: {
        Footer,
    },
    setup() {
        const $getSessionStorage = inject('$getSessionStorage');
        const user = ref({})
        const router = useRouter();
        const point = ref(0);
        const convertGenderText = (num) => {
            if (num == 1) return '男';
            else return '女';
        }

        const logout = () => {
            sessionStorage.removeItem('user');
            router.push({ path: '/login' });
        }

        onMounted(()=>{
            user.value =$getSessionStorage('user');
            console.log(user)
            axios.post('UserController/getPointById', qs.stringify({
                userId: user.value.userId
            })).then(response =>{
                point.value = response.data;
            }).catch(error => {
                console.error(error);
            });
        })
        
        return {
            user,
            point,
            convertGenderText,
            logout
        }
    }


};
</script>


<style>
/* 样式定义 */
.wrapper {
    width: 100%;
    height: 100%;
    background-color: #fefefe;
}

.wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #0097FF;
    color: #fff;
    font-size: 4.8vw;
    /* position: fixed; */
    left: 0;
    top: 0;
    z-index: 1000;
    display: flex;
    justify-content: center;
    align-items: center;
}

.profile-header {
    text-align: center;
    margin-bottom: 20px;
}

.profile-picture {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    object-fit: cover;
    margin-bottom: 5px;
    margin-top: 10px;
}

.profile-details {
    padding: 10px 20px 10px 20px;
    background-color: #f3f3f3;
    border-radius: 3px;
    margin: 20px
}

.detail-item {
    margin-bottom: 10px;
}

.container {
    margin: 20px;
    background-color: #f3f3f3;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 3px;
}

.logout-Button {
    display: inline-block;
    padding: 0.5rem 1rem;
    background-color: transparent;
    border: none;
    color: #585b5e;
    font-size: 16px;
    /* text-decoration: underline; */
    cursor: pointer;
    text-align: center;
}

/* 更多样式定义 */
</style>
