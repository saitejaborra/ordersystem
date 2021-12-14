import React from "react";
import "./stylesheets/errorbox.css";
const ErrorBox=({message,resetsteps})=>{
    return( 
    <div className="error">
    {/* <h2>{message.includes("completed successfully") ? "Congrats":"Error"}  </h2> */}
    <h2>{message.includes("successfully") ? "Congrats" : "Something wrong"}</h2>
    <p>{message}</p>
    <button id="errorbutton" onClick={resetsteps}>Got it!</button>
    </div>
    )
}
export default ErrorBox;