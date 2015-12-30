import React from 'react';
import { Label, Glyphicon } from 'react-bootstrap';


const ExitCode = React.createClass({
    getStyle() {
        if (this.props.result == 0) {
            return "success"
        } else {
            return "danger"
        }
    },
    getText() {
        if (this.props.result == 0) {
            return " Yeyy! - Exit Code is : " + this.props.result
        } else {
            return " Opps! - Exit Code is : " + this.props.result
        }
    },
    getIcon() {
        if (this.props.result == 0) {
            return "ok"
        } else {
            return "fire!"
        }
    },
    render() {
        return (
            <div>
                <h2>
                    <Label bsStyle={this.getStyle()}>
                        <Glyphicon glyph={this.getIcon()}/>&nbsp;{this.getText()}
                    </Label>
                </h2>
            </div>
        );
    }
});

export default ExitCode;
