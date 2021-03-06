import * as React from "react";

/* The available editors for the field */
type Editor = "textbox" | "multilinetextbox" | "email" | "telephone" | "numeric" | "dropdown";

export interface IFieldProps {
    /* The unique field name */
    id: string;

    /* The label text for the field */
    label?: string;

    /* The editor for the field */
    editor?: Editor;

    /* The drop down items for the field */
    options?: string[];

    /* The field value */
    value?: any;
}

export const Field: React.FunctionComponent<IFieldProps> = ({
                                                                id,
                                                                label,
                                                                editor,
                                                                options,
                                                                value
}) => {
    return (
        <div className="form-group">
            {label && <label htmlFor={id}>{label}</label>}

            {editor!.toLowerCase() === "textbox" && (
                <input
                    id={id}
                    type="text"
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control"
                />
            )}

            {editor!.toLowerCase() === "multilinetextbox" && (
                <textarea
                    id={id}
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLTextAreaElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLTextAreaElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control"
                />
            )}

            {editor!.toLowerCase() === "email" && (
                <input
                    id={id}
                    type="email"
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control"
                />
            )}

            {editor!.toLowerCase() === "telephone" && (
                <input
                    id={id}
                    type="tel"
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control"
                />
            )}

            {editor!.toLowerCase() === "numeric" && (
                <input
                    id={id}
                    type="number"
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLInputElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control"
                />
            )}

            {editor!.toLowerCase() === "dropdown" && (
                <select
                    id={id}
                    name={id}
                    value={value}
                    onChange={
                        (e: React.FormEvent<HTMLSelectElement>) =>
                            console.log(e) /* TODO: push change to form values */
                    }
                    onBlur={
                        (e: React.FormEvent<HTMLSelectElement>) =>
                            console.log(e) /* TODO: validate field value */
                    }
                    className="form-control">
                    {options &&
                    options.map(option => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
            )}

            {/* TODO - display validation error */}
        </div>
    );
};

Field.defaultProps = {
    editor: "textbox"
};