import * as React from "react";
import { Form } from "./Form";
import { Field } from "./Field";

export const RegistrationForm: React.FunctionComponent = () => {
    return (
        <Form
            action="http://localhost:4351/api/register" // Not a real endpoint
            render={() => (
                <React.Fragment>
                    <div className="alert alert-info" role="alert">
                        Please enter all the available information.
                    </div>
                    <Field id="firstName" label="First Name" />
                    <Field id="lastName" label="Last Name" />
                    <Field id="npiNumber" label="NPI Number" editor="numeric"/>
                    <Field id="businessAddress" label="Business Address" editor="multilinetextbox" />
                    <Field id="telephoneNumber" label="Telephone Number" editor="telephone" />
                    <Field id="emailAddress" label="Email Address" editor="email" />
                </React.Fragment>
            )}
        />
    );
};