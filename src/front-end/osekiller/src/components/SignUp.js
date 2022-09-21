import SignUpForm from "./forms/SignUpForm";
import { useFormik } from 'formik';
import * as Yup from "yup"
import axios from "axios";
import { useState } from "react";

const SignUp = (props) => {
    const [userType, setUserType] = useState("etudiant");
    const [isOpen, setIsOpen] = useState(false)
    const [hasOpenError, setHasOpenError] = useState(false)

    const handleClose = (reason) => {
        if (reason === 'clickaway') {
          return;
        } 
        setHasOpenError(false)
        setIsOpen(false);
    };
    
    const formikStudentOrManager = useFormik({
        initialValues : {
            nom : "",
            prenom : "",
            email : "",
            password : "",
            passwordConfirmation : ""
        },
        validationSchema : Yup.object({
            nom : Yup.string().required("Requis"),
            prenom : Yup.string().required("Requis"),
            email : Yup.string().required("Requis").email("Pas un email valide"),
            password : Yup.string().required("Requis"),
            passwordConfirmation : Yup.string().required("Requis").oneOf([Yup.ref('password')], "le mot de passe n'est pas le même"),
        }),
        onSubmit: values => {
            axios.post(`https://${process.env.REACT_APP_SERVER_ADRESS}/${userType === "etudiant" ? "student" : "manager"}/signUp`, {
                firstName : values.prenom,
                lastName : values.nom,
                email : values.email,
                password : values.password
            })
            .then((response) => {
                console.log(response.data)
                setIsOpen(true)
            })
            .catch((error) => {
                console.log(error)
                setHasOpenError(true)
            })
          },
          });

    const formikCompany = useFormik({
        initialValues : {
            nom : "",
            email : "",
            password : "",
            passwordConfirmation : ""
        },
        validationSchema : Yup.object({
            nom : Yup.string().required("Requis"),
            email : Yup.string().required("Requis").email("Pas un email valide"),
            password : Yup.string().required("Requis"),
            passwordConfirmation : Yup.string().required("Requis").oneOf([Yup.ref('password')], "le mot de passe n'est pas le même"),
        }),
        onSubmit: values => {
            axios.post(`https://${process.env.REACT_APP_SERVER_ADRESS}/company/signUp`, {
                companyName : values.nom,
                email : values.email,
                password : values.password
            })
            .then((response) => {
                console.log(response.data)
                setIsOpen(true)
            })
            .catch((error) => {
                console.log(error)
                setHasOpenError(true)
            })
          },
    });


    return (
        <SignUpForm 
        changeForm={props.handleChangeForm} 
        formikCompany={formikCompany} 
        formikStudentOrManager={formikStudentOrManager}
        userType={userType} 
        isOpen={isOpen} 
        hasOpenError={hasOpenError} 
        handleClose={handleClose} 
        setUserType={setUserType} 
        title={props.title} />
    )
}

export default SignUp ;