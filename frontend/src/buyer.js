import axios from 'axios';
import React, { useState } from 'react';
import './stylesheets/buyandsell.css';
// import {Link} from 'react-router-dom';
const Buyer=({authenticatebuyer,authenticatebuyorder, calcprice, calcquant, setInstrument,instrument, buyer})=>{

const authenticateinstrument=(e)=>{
axios.get(`http://localhost:8082/instruments/getinstrument/${e.target.value}`)
.then((response)=>{
    setInstrument(response.data);
}).catch((error)=>{
    console.log(error);
})
}
    return(
    <div className="buy-sell" >
        <label for="cid" >Client ID</label>
    <input type="text" id="cid" placeholder="Enter Client-ID" autocomplete="off" onBlur={authenticatebuyer}/><br/>
       {
           buyer.clientName==null? null: <h4>Client Name:&nbsp;{buyer.clientName}</h4>
        }
          {/* {
           buyer.custodian.custodianId==null? null: <h4>Custodian Name:&nbsp;{buyer.custodian.custodianName}</h4>
        } */}
    <label for="iid">Instrument ID</label>
    <select name="idtype" id="iid" onInput={authenticateinstrument}>
            <option selected value="none">select</option>
             <option value="I001">I001</option>
             <option value="I002">I002</option>
             <option value="I003">I003</option>
             <option value="I004">I004</option>
            </select>
            {
            instrument.instrumentName==null? null : <h4>Instrument Name: &nbsp; {instrument.instrumentName}</h4>
             }
          {
         instrument.faceValue==null? null : <h4>Face value: &nbsp; &#8377; {instrument.faceValue}</h4>
          }
          {
    instrument.expiryDate==null? null : <h4>Expiry Date: &nbsp; {instrument.expiryDate}</h4>
          }
    <label for="price" >Price:</label>
    <input type="number" id="price" placeholder="Enter Price" onChange={calcprice}/><br/>
    <label for="quantity" >Quantity:</label>
    <input type="number" id="quantity" placeholder="Enter Quantity multiples of 25" onChange={calcquant}/><br/>
    <button className="buy-sell-button" onClick={authenticatebuyorder} value="BUY">BUY</button>
    </div>
    )
}
export default Buyer;