const { createApp } = Vue

  createApp({
    data() {
      return {
        email: '',
        password: '',
        clients: ''
      }
    },
    methods: {
      getData(){        
        axios
        .get('/api/clients/current')
        .then(response => {
            this.clients = response.data
            this.accounts = response.data.account
            this.transactions = response.data.transactions
            this.loans = this.clients.clientLoans
            this.activeAccounts = this.accounts.filter(account => account.activeAccount == true)
        }) 
    },
      login() {
          axios.
          post('/api/login', `email=${this.email}&password=${this.password}`, {
              headers: {
                  'content-type': 'application/x-www-form-urlencoded'
              }
          })
          .then((response) => {
            if(this.email.includes("@admin.com")){
              Swal.fire({
                icon: 'success',
                title: 'Successful Login Admin Role',
                showConfirmButton: true,
                timer: 1500
              }).then(() => window.location.href = '/admin/manager.html')
            } else{
              Swal.fire({
                icon: 'success',
                title: 'Successful Login',
                showConfirmButton: false,
                timer: 1500
              }).then(() => window.location.href = '/web/accounts.html')
            }
          })
          .catch((error) =>{
            if(this.email == ""){
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: "Missing Email",
                confirmButtonColor: "#028484",
              });
            }
            if(this.password == ""){
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: "Missing Password",
                confirmButtonColor: "#028484",
              });
            }
            if(!this.email.includes("@")){
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: "Invalid Email",
                confirmButtonColor: "#028484",
              });
            }
            if(!this.email.includes(".com")){
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: "Invalid Email",
                confirmButtonColor: "#028484",
              });
            }
            if(this.password.length < 8){
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: "Invalid Password",
                confirmButtonColor: "#028484",
              });
            } else{
              Swal.fire({
                icon: "error",
                title: "Upss..",
                text: error.response.data,
                confirmButtonColor: "#028484",
              });
            }
          })
        },
    getData(){       
      axios
      .get('/api/clients/current')
      .then(response => {
          this.clients = response.data
          this.accounts = response.data.account
          this.transactions = response.data.transactions
          
      })
      axios
      .get('/api/clients/email')
      .then(response => {
        this.clientsEmail = response.data
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

  }).mount('#app')