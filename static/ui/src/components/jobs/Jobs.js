import React from 'react';

import NavMain from './../navigation/NavMain';
import PageHeader from './../PageHeader';
import PageFooter from './../PageFooter';
import {Button, Glyphicon} from 'react-bootstrap';
import $ from 'jquery';

var View1Table = React.createClass({
    render: function () {
        return (
            <table className="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>Name</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {
                    this.props.data.map(function (row) {
                        return <View1Row key={row.name} data={row}/>
                    })}
                </tbody>
            </table>
        );
    }
});

var View1Row = React.createClass({
    render: function () {
        return (
            <tr>
                <td>{this.props.data.name}</td>
                <td>
                    <Button bsStyle="link" href={"/job/" + this.props.data.name}>
                        <Glyphicon glyph="edit"/>
                        Edit
                    </Button>
                </td>
            </tr>
        );
    }
});


const Jobs = React.createClass({

    loadData() {
        return $.getJSON("http://localhost:8080/jobs");
    },

    getInitialState() {
        return {data: []};
    },

    componentDidMount(){
        this.loadData().success(function (data) {
            if (this.isMounted()) {
                console.log(data)
                this.setState({data: data});
            }
        }.bind(this))
    },

    render() {
        return (
            <div>
                <NavMain activePage="jobs"/>

                <PageHeader
                    title="Jobs Page"
                    subTitle="List of jobs will be here."/>

                <div className="container bs-docs-container">
                    <View1Table data={this.state.data}/>

                </div>
                <PageFooter />
            </div>
        );
    }
});

export default Jobs;