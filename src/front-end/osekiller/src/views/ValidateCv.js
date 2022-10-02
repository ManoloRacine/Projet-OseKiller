import axios from "../api/axios";
import { useEffect, useState } from "react";


const ValidateCv = () => {
    const [pdf, setPdf] = useState("");

    useEffect(() => {
        axios.get(
            "/student/1/cv", {
                responseType: "arraybuffer",
                headers: {
                    "Content-Type" : "application/pdf",
                    Authorization: localStorage.getItem("accessToken"),
                },
            }
        ).then((response) => {
            console.log("test") ;
            //let data_url = URL.createObjectURL(response.data) ;
            let link = document.createElement('a');
            var blob1 = new Blob([response.data], {type: "application/pdf"});
            var blob2 = new File([blob1], "1.pdf", {type: "application/pdf"});
            
            var data_url = window.URL.createObjectURL(blob1) ;
            setPdf(data_url) ;
            console.log(pdf) ;
            console.log(blob1) ;
            console.log(localStorage.getItem("accessToken")) ;
        })
    }, [])

    return (
        <div>
            <iframe type="application/pdf" src={pdf} height="500px" width="50%"></iframe>
        </div>
        
    )
    
}

export default ValidateCv;