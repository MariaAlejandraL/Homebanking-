const { createApp } = Vue

createApp({
    data() {
    return {
    clients: "",
     type:"",
     color:"",
     cards: "",
     cardsActive:"",
     selectCard: "",
     cardApply: "",
     cardID:"",
     expirationDate: "",
     currentDate: ""
    }
    },
    created (){
    this.getData();

    this.currentDate = new Date(Date.now()); 
    this.currentDate = this.currentDate.getFullYear();
    this.currentDate = this.currentDate.toString().slice(2,4)
    
    
},
    methods:{
    getData(){
        axios
        .get('/api/clients/current')
        .then(response => {
        this.clients = response.data
        this.accounts = response.data.account
        this.loans = response.data.clientLoans
        this.cards = response.data.clientCards
        this.cardsActive = this.cards.filter(card => card.activeCard == true)
        })
        },
    getSelectCard(){
        axios
        .get('/api/clients/current')
        .then(response => {
        this.cards = response.data.clientCards
        this.selectCard = this.cards.filter(
            (card) => card.number == this.cardApply
          );
          this.cardID = this.selectCard[0].id
        })
        },
    newCard(){
        axios.post(`/api/clients/current/cards?cardType=${this.type}&cardColor=${this.color}`,  { headers: { "content-type": "application/x-www-form-urlencoded" } })
        .then(()=> Swal.fire({
            icon: 'question',
            title: 'Are you sure with the new card?',
            confirmButtonColor: "#028484",
          }))
        .then(response => window.location.href = "/web/cards.html")
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

    },
    deleteCard(id) {
        axios.patch('/api/clients/current/cards/delete/'+ id)
        .then( () => Swal.fire(
            'Delete Card',
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