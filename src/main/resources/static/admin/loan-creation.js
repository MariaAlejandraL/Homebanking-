
const app = Vue.

createApp({
  data() {
    return {
      loans: [],
      newLoanName: "",
      newLoanAmount: "",
      newLoanPayments: "",
      newLoanPercentage: ""
    };
  },
  created() {
    this.getData();
    this.getLoans();
  },
  methods: {
    getData() {
      axios.get("/api/clients/current").then((response) => {
        this.admin = response.data
      });
    },
    getLoans() {
      axios.get("/api/loans").then((response) => {
        this.loans = response.data;
      });
    },
    createLoan() {
      axios.post('/api/loans/admin',`name=${this.newLoanName}&maxAmount=${this.newLoanAmount}&payments=${this.newLoanPayments}&percentage=${this.newLoanPercentage}`)
          .then( () => Swal.fire(
            'Create Loan',
            'Are you sure to Create Loan?',
            'question'
          ))
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
  },
}).mount("#app");
