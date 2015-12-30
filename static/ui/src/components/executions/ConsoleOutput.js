import React from 'react';
import { Label } from 'react-bootstrap';
import $ from 'jquery';


const ConsoleOutput = React.createClass({
    loadData() {
        return $.ajax({
            type: "GET",
            url: "http://localhost:8080/job/" + this.props.jobName + "/execution/" + this.props.executionId + "/log",
            contentType: 'text/plain',
        });
    },
    getInitialState() {
        return {logs: ''};
    },
    componentDidMount(){
        this.loadData().success(function (data) {
            if (this.isMounted()) {

                this.setState({logs: data});
            }
        }.bind(this))
    },
    render() {
        return (
            <div>
                <h2><Label bsStyle="primary">Console Output</Label></h2>
                <pre className="console-output">{this.state.logs}</pre>

            </div>
        );
    }
});

export default ConsoleOutput;
