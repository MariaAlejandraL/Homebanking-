const { createApp } = Vue

createApp({
    data() {
    return {
    accounts: "",
    allAccounts: "",
    amount: "",
    description: "",
    originAccount: "",
    destinyAccount: "",
    activeAccounts:"",
    allActiveAccounts:""
    }
    },
    created (){
    this.getData();
    },
    methods:{
    getData(){
        axios
        .get('/api/clients/current')
        .then(response => {
        this.accounts = response.data.account
        this.activeAccounts = this.accounts.filter(account => account.activeAccount == true)
        })
        axios
        .get('/api/accounts')
        .then(response => {
        this.allAccounts = response.data
        this.allActiveAccounts = this.allAccounts.filter(account => account.activeAccount == true)
        })
        },
    newTransaction(){
        axios.post(`/api/transactions?amount=${this.amount}&description=${this.description}&originAccount=${this.originAccount}&destinyAccount=${this.destinyAccount}`,  { headers: { "content-type": "application/x-www-form-urlencoded" } })
        .then(()=> Swal.fire({
            icon: 'question',
            title: 'Are you sure with the transaction?',
            confirmButtonColor: "#028484",
          }))
        .then(response => window.location.href = "/web/accounts.html",)
        .catch(error => {
          Swal.fire({
              icon: 'error',
              title: 'Oops... ',
              text: error.response.data,
              confirmButtonColor: "#028484",
            })
        });
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
    }

}).mount('#app')