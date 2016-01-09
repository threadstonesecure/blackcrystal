import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { jobsURI } from '../utils/Config'

import { Link } from 'react-router';
import {Button, Glyphicon, Grid, Row, Col, Navbar, Nav, Modal} from 'react-bootstrap';
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
    open() {
        if (window.confirm("Are you sure you want to delete this job?")) {
            console.log("ok");
        }
    },
    render: function () {
        return (
            <tr>
                <td>{this.props.data.name}</td>
                <td>
                    <Button bsStyle="link" onClick={this.open}>
                        <Glyphicon glyph="remove"/>
                        Delete
                    </Button>
                   <Link to={"/job/" + this.props.data.name}>
                        <Glyphicon glyph="edit"/>
                        Edit
                    </Link>
                    <Link to={"/job/" + this.props.data.name+"/executions"}>
                      <Glyphicon glyph="tasks"/>
                        Executions
                    </Link>
                </td>
            </tr>
        );
    }
});


const Jobs = React.createClass({

    loadData() {
        return $.getJSON(jobsURI());
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

                <Grid>
                    <Row>
                        <Navbar inverse>
                            <Nav pullRight>
                                <Link to="/new/job"> <Glyphicon glyph="plus"/> Add
                                    Job </Link>
                            </Nav>
                        </Navbar>
                    </Row>
                    <Row>
                        <Col>
                            <View1Table data={this.state.data}/>
                        </Col>
                    </Row>

                </Grid>

                <PageFooter />
            </div> 
        );
    }
});

export default Jobs;
