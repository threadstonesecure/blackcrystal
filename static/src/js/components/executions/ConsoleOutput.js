import React from 'react';
import { Label } from 'react-bootstrap';
import $ from 'jquery';
import { jobExecutionLogURI } from '../../utils/Config'




const ConsoleOutput = React.createClass({
    loadData() {
        return $.ajax({
            type: "GET",
            url: jobExecutionLogURI(this.props.jobName, this.props.executionId),
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
             <div className="box">
                <div className="box-header with-border">
                    <h3 className="box-title">Console Output</h3>
                </div>
             <div className="box-body" >
                 <pre className="console-output">{this.state.logs}</pre>
            </div>
           </div>
        );
    }
});

export default ConsoleOutput;
