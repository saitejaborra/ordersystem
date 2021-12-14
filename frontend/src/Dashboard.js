import axios from 'axios';
import React, { useState } from 'react';
import "./stylesheets/dashboard.css";



const renderobj=(resultset, clientset)=>{

    const obj= clientset.map((item)=>{
        return resultset.map((item2)=>{
            
            if(item2.clientId==item.clientId && (item2.totalBuy || item2.totalSell)>0)
            {
            return( 
            <tr>
            <td>{item2.clientId}</td> 
            <td>&#8377; {item2.totalBuy}</td> 
            <td>&#8377; {item2.totalSell}</td> 
           </tr>
            )
            }
            else
            return null;
        })
    })
    return obj;
}
const sumOfall=(totaltransaction)=>{
    let val=localStorage.getItem("custodiannum");
    const buyvalue=totaltransaction.reduce((accumulator, current)=>accumulator+current.totalBuy,0)
    const sellvalue=totaltransaction.reduce((accumulator, current)=>accumulator+current.totalSell,0)
    const obj=totaltransaction.filter((element)=>{
        if(element.custodianId==val)
        return element});
    return (
        <>
        <tr>
        <th>{obj.map((item)=>item.custodianId)} Total:</th>
        <th>&#8377; {obj.map((item)=>item.totalBuy)}</th>
        <th>&#8377; {obj.map((item)=>item.totalSell)}</th>
    </tr>
        <tr>
            <th>All Custodians value:</th>
            <th>&#8377; {buyvalue}</th>
            <th>&#8377; {sellvalue}</th>
        </tr>
        </>
    )
}


const Selector=({changed})=>{
    return(
    <select name="idtype" id="dashboard-iid" onInput={changed}>
            <option selected value="0">select a custodian</option>
             <option value="CS001">CS001</option>
             <option value="CS002">CS002</option>
             <option value="CS003">CS003</option>
             <option value="CS004">CS004</option>
             <option value="CS005">CS005</option>
             <option value="CS006">CS006</option>
            </select>
    )
}
const Tablerender=({resultset,clientset, totaltransaction})=>{
    return(
        <div > 
            <table className="tabledata" >
                <tr>       
                    <th>Client</th>
                    <th>Buy</th>
                    <th>Sell</th>
                </tr>
                    {
                       renderobj(resultset,clientset)
                    }
                    {
                        sumOfall(totaltransaction)
                    }

                
            </table>
        </div>
    )
}

    const Dashboard=()=>{
        const [clientset,setClientset]=useState([]);
        const [resultset,setResultset]=useState([]);
        const [totaltransaction, setTotaltransaction]=useState([]);
        const [custodiantotal, setCustodiantotal]=useState();
    
        
        const changed=(e)=>{
            localStorage.setItem("custodiannum",e.target.value);
        axios.get(`http://localhost:8082/client/clientsByCustodian/${e.target.value}`)
        .then((response)=>{
            setClientset(response.data);
            axios.get(`http://localhost:8082/clientWiseStats`)
            .then((response)=>{
                setResultset(response.data);
            axios.get(`http://localhost:8082/custodianWiseStats`)
            .then((response)=>{
                      setTotaltransaction(response.data);
            })
        
            })
        }).catch((error)=>{
            console.log(error);
        })
        }
        return(
            <>
            <Selector changed={changed}/>
            <Tablerender totaltransaction={totaltransaction} resultset={resultset} clientset={clientset}/>
            </>
        )
    }
export default Dashboard;