import React, { useState } from 'react';
import './stylesheets/App.css';
import Seller from './seller';
import Buyer from './buyer';
import Navbar from './navbar';
import AgreementLogo from './agreementmodel';
import ErrorBox from './errorbox';
import Dashboard from './Dashboard';
import axios from 'axios';

function App() {

let [buyer,setBuyer]=useState([])

const authenticatebuyer=(e)=>{
axios.get(`http://localhost:8082/client/getclient/${e.target.value}`)
.then((response)=>{
         setBuyer(response.data);
}
).catch((error)=>{
    console.log("not fetching");
})
}
let price
let quantity

let purchasevalue;
const calcprice=(e)=>{
  price= e.target.value;
  
}
const calcquant=(e)=>{
  quantity=e.target.value;
  total(price,quantity);
}
const total=(price,quantity)=>{
  purchasevalue=price*quantity;
}


let [instrument,setInstrument]=useState([]);

const authenticatebuyorder=(e)=>{

  if(purchasevalue<=buyer.transactionLimit && buyer.transactionLimit!=null&&(quantity%25)===0){
    const obj={
      clientId:buyer.clientId,
      instrumentId:instrument.instrumentId,
      limitOrder:true,
      orderDirection:e.target.value,
      price:price,
      quantity:quantity
    };
    console.log(obj);
    axios.post("http://localhost:8082/transaction",obj)
    .then((response)=>{
      setMessage("Buy order successfully placed");
      setSteps(2);
    }
    ).catch((error)=>{
      setMessage("Not able to place the order");
      setSteps(steps+1);
    })
  }
  else {
    setMessage("Enter all details correctly for proper bidding");
      setSteps(steps+1);
  }
}

let [seller,setSeller]=useState([])
let [sellerinstrument,setSellerInstrument]=useState([]);

const authenticatesellorder=(e)=>{
  if(purchasevalue<=seller.transactionLimit && seller.transactionLimit!=null&&(quantity%25)===0){
    const obj={
      clientId:seller.clientId,
      instrumentId:sellerinstrument.instrumentId,
      limitOrder:true,
      orderDirection:e.target.value,
      price:price,
      quantity:quantity
    };
    axios.post("http://localhost:8082/transaction",obj)
    .then((response)=>{
      setMessage("Sell Order successfully placed");
      setSteps(2);
    }
    ).catch((error)=>{
      setMessage("Not able to place the order");
      setSteps(2);
     
    })
  }
  else{
    setMessage("Enter all details correctly for proper bidding");
    setSteps(2);
  }
}

const authenticateseller=(e)=>{
  console.log(e.target.value)
axios.get(`http://localhost:8082/client/getclient/${e.target.value}`)
.then((response)=>{
         console.log(response.data);
         setSeller(response.data);
}
).catch((error)=>{
    console.log("not fetching");
})
}

const [home,setHome]=useState(true);
const [steps,setSteps]=useState(1);

const resetsteps=()=>{
  setSteps(1);
}
const [message,setMessage]=useState();


const selector=(home)=> {
  if (home) {
    switch(steps){
      case 1:
    return (
      <div className="app">
        <Buyer authenticatebuyer={authenticatebuyer} purchasevalue={purchasevalue} calcprice={calcprice} calcquant={calcquant} authenticatebuyorder={authenticatebuyorder} instrument={instrument} setInstrument={setInstrument} buyer={buyer} />
        <AgreementLogo />
        <Seller authenticateseller={authenticateseller} calcprice={calcprice} calcquant={calcquant} authenticatesellorder={authenticatesellorder} sellerinstrument={sellerinstrument} setSellerInstrument={setSellerInstrument} seller={seller} />
      </div>
    );
    case 2:
      return <ErrorBox resetsteps={resetsteps} message={message}/>
    }
  }
  else {
    return <Dashboard />;
  }
}
  
const homefunction=()=>{
  setHome(prev=>!prev);
  }

  return (
    <>
    <Navbar homefunction={homefunction} home={home} />
    {
      selector(home)
    } 
      </>
  );
}

export default App;
