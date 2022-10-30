const { createApp } = Vue;

createApp({
  data() {
    return {
      name: "",
      lastName: "",
      email: "",
      password: "",
    };
  },
  methods: {
    register() {
      axios
          .post(
          "/api/clients",
          "name=" +
              this.name +
              "&lastName=" +
              this.lastName +
              "&email=" +
              this.email +
              "&password=" +
              this.password,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
          )
          .then(()=> Swal.fire({
                title: 'Registered, Go Home and Login',
                confirmButtonColor: "#028484",
              }))
          .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'Oops... ',
                text: error.response.data,
                confirmButtonColor: "#028484",
              })
          });
      },
    getData(){        
      axios
      .get('/api/clients/current')
      .then(response => {
          this.clients = response.data
          this.accounts = response.data.account
          this.transactions = response.data.transactions
          
      })
      
  },
  logout() {
   axios.post('/api/logout')
    .then( () => Swal.fire(
      'Logout?',
      'Are you sure to leave your homebanking?',
      'question'
    ))
    .then(() => window.location.href = '/web/index.html')

}
  },
}).mount("#app");
