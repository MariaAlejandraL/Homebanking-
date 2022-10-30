const { createApp } = Vue

createApp({
    data() {
    return {
        clients: "",
        accounts: "",
        transactions:"" ,
        loans: "",
        idAccount:"",
        activeAccounts: "",
        selectAccount: "",
        accountApply:"",
        accountID:"",
        accountType: "",
        showModal: false
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
            this.clients = response.data
            this.accounts = response.data.account
            this.transactions = response.data.transactions
            this.loans = this.clients.clientLoans
            this.activeAccounts = this.accounts.filter(account => account.activeAccount == true)
        }) 
    },
    getSelectAccount(){
        axios
        .get('/api/clients/current')
        .then(response => {
            this.accounts = response.data.account
            this.selectAccount = this.accounts.filter(
                (account) => account.number == this.accountApply
              );
              this.accountID = this.selectAccount[0].id

        }) 
    },
    createAccount(){
        this.showModal = false,
        axios.post(`/api/clients/current/accounts?accountType=${this.accountType}`,  { headers: { "content-type": "application/x-www-form-urlencoded" } })
        .then( () => Swal.fire(
            "Remember:  you can't create more than 3 accounts",
          ))
        .then(() => window.location.href = '/web/accounts.html')
    },
    logout() {
        axios.post('/api/logout')
        .then( () => Swal.fire(
          'Logout?',
          'Are you sure to leave your homebanking?',
          'question'
        ))
        .then(() => window.location.href = '/web/index.html')

    },
    deleteAccount(id) {
        axios.patch('/api/clients/current/accounts/delete/'+ id)
        .then( () => Swal.fire(
            'Delete Account',
            'Are you sure?',
            'question'
          ))
            .then(response => {
                return window.location.reload()
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops... ',
                    text: error.response.data,
                    confirmButtonColor: "#028484",
                  })
              });
    },
    }

}).mount('#app')