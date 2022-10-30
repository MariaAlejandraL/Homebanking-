const { createApp } = Vue

createApp({
    data() {
    return {
        clients: [],
        transactions: [],
        account: [],
        accounts:[],
        idAccount:[],
        heading: "Generate Transactions PDF",
    }
    },
    created (){
    this.getData();
    },
    methods:{
    getData(){
        const urlParams = new URLSearchParams(window.location.search);
        const Id = urlParams.get('id');

        axios
        .get(`/api/accounts/${Id}`)
        .then(response => {
                this.account = response.data;
                this.transactions = this.account.transactions
            })
        axios
        .get('/api/clients/current')
        .then(response => {
            this.clients = response.data
            this.accounts = response.data.account
            this.loans = this.clients.clientLoans
            this.accounts = this.clients.account.filter(account => account.id == Id)
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
    }

}).mount('#app')