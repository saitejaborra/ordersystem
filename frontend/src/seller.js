import React, {useState} from 'react';
import './stylesheets/buyandsell.css';
import axios from 'axios';
// import {Link} from 'react-router-dom';
const Seller=({authenticateseller,sellerinstrument, authenticatesellorder, calcprice, calcquant, setSellerInstrument, seller})=>{

    const authenticateinstrument=(e)=>{
    axios.get(`http://localhost:8082/instruments/getinstrument/${e.target.value}`)
    .then((response)=>{
        setSellerInstrument(response.data);
    }).catch((error)=>{
        console.log(error);
    })
    }
    return(
        <div className="buy-sell" >
        <label for="cid" >Client ID</label>
    <input type="text" id="cid" placeholder="Enter Client-ID" autocomplete="off" onBlur={authenticateseller}/><br/>
       {
           seller.clientName==null? null: <h4>Client Name: &nbsp;{seller.clientName}</h4>
        }
    <label for="iid">Instrument ID</label>
    <select name="idtype" id="iid" onInput={authenticateinstrument}>
            <option selected value="none">select</option>
             <option value="I001">I001</option>
             <option value="I002">I002</option>
             <option value="I003">I003</option>
             <option value="I004">I004</option>
            </select>
            {
            sellerinstrument.instrumentName==null? null : <h4>Instrument Name: &nbsp; {sellerinstrument.instrumentName}</h4>
}
          {
         sellerinstrument.faceValue==null? null : <h4>Face value: &nbsp; &#8377; {sellerinstrument.faceValue}</h4>
          }
          {
    sellerinstrument.expiryDate==null? null : <h4>Expiry Date: &nbsp; {sellerinstrument.expiryDate}</h4>
          }
    <label for="price" >Price:</label>
    <input type="number" id="price" placeholder="Enter Price" onChange={calcprice} /><br/>
    <label for="quantity" >Quantity:</label>
    <input type="number" id="quantity" placeholder="Enter Quantity multiples of 25" onChange={calcquant}/><br/>
    <button className="buy-sell-button" style={{backgroundColor:'#cc0000'}} value="SELL" onClick={authenticatesellorder} >SELL</button>
    </div>
    )
}
export default Seller;