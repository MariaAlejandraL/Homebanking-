const { createApp } = Vue;

createApp({
  data() {
    return {
      accounts: [],
      loans: [],
      loanApply: "",
      paymentApply: "",
      amountApply: "",
      accountApply: "",
      amount: "",
      payments: "",
      payment: "",
      destiny: "",
      maxAmounts: "",
      selectLoan: "",
      currentAccounts: "",
      loanID: "",
      activeAccounts: ""
    };
  },
  created() {
    this.getData();
    this.getLoans();
  },
  methods: {
    getData() {
      axios.get("/api/clients/current").then((response) => {
        this.accounts = response.data.account;
        this.activeAccounts = this.accounts.filter(account => account.activeAccount == true)
      });
    },
    getLoans() {
      axios.get("/api/loans").then((response) => {
        this.loans = response.data;
        this.selectLoan = this.loans.filter(
          (loan) => loan.name == this.loanApply
        );
        this.loanID = this.selectLoan[0].id
        this.payment = this.selectLoan[0].payments;
        this.maxAmounts = this.selectLoan[0].maxAmount;
      });
    },
    createLoan() {
      axios
        .post(
          "/api/loans",
          {
            "id": this.loanID,
            "amount": this.amountApply,
            "payments": this.paymentApply,
            "destinyAccount": this.accountApply,
          })
          .then( () => Swal.fire(
            'Loan Apply',
            'Are you sure to Apply?',
            'question'
          ))
        .then(() => (window.location.href = "/web/accounts.html"))
        .catch(error => {
          Swal.fire({
              icon: 'error',
              title: 'Oops... ',
              text: error.response.data,
              confirmButtonColor: "#028484",
            })
        });
    },
    logout(){
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
