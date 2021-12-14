import React from "react";
import Svg from "./logo";
import './stylesheets/navbar.css';

const Navbar=({homefunction,home})=>{

    return(
        <div className="navbar">
        <div className="fulllogo">
        <Svg/>
        <p>Trading Platform</p>
        </div>
        <button className="dashboard-button" onClick={homefunction}>{home==true ? "Dashboard" : "Home" }</button>
        </div>
    )

}
export default Navbar;