import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { Link } from 'react-router';
import {Button, Alert,Grid,Row,Col,Navbar,Nav,NavDropdown,MenuItem,NavItem, Glyphicon} from 'react-bootstrap';
import $ from 'jquery';


const NoResourceAlert = React.createClass({
    render: function () {
        return (
            <Alert bsStyle="warning">
                <strong>There are no resources! </strong> You need to add a resource to start working, with jobs!
            </Alert>
        );
    }
});


var View1Table = React.createClass({
    render: function () {
        return (
            <table className="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>Name</th>
                    <th></th>
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
                    <Button bsStyle="link" href="#">
                        <Glyphicon glyph="edit"/>
                        Edit
                    </Button>
                </td>
                <td>
                    <Button bsStyle="link" href="#">
                        <Glyphicon glyph="remove"/>
                        Delete
                    </Button>
                </td>
            </tr>
        );
    }
});


const Resources = React.createClass({

    loadData() {
        return $.getJSON("http://localhost:8080/resources");
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
                <NavMain activePage="resources"/>
                <PageHeader title="Resources" subTitle=""/>

                <Grid>
                    <Row>
                        <Navbar inverse>
                            <Nav pullRight>
                                <Link to="/new/resource"> <Glyphicon glyph="plus"/> Add
                                    Resource </Link>
                            </Nav>
                        </Navbar>
                    </Row>
                    <Row>
                        <Col>
                            {
                                (this.state.data == null || this.state.data.length == 0 )
                                    ? <NoResourceAlert/> : <View1Table data={this.state.data}/>
                            }
                        </Col>
                    </Row>

                </Grid>

                <PageFooter />
            </div>
        );
    }
});

export default Resources;
