import React from 'react';

import NavMain from '../navigation/NavMain';
import PageHeader from '../PageHeader';
import PageFooter from '../PageFooter';
import ResourceType from './ResourceType';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import {Button, Alert,Grid,Row,Col,Navbar,Nav,NavDropdown,MenuItem,NavItem, Glyphicon,Input} from 'react-bootstrap';
import $ from 'jquery';


const Resource = React.createClass({
    mixins: [LinkedStateMixin],
    save(event){
        this.state
        var resource = this.refs.resourceType.collect()
        resource.name = this.state.name;
        this.put(resource);
        console.log(resource);
    },
    put(data) {
        var request = $.ajax({
            type: "PUT",
            url: "http://localhost:8080/resource",
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: "json"
        });

        request.done(function (msg) {
            //TODO use react-router transitionTo
            $(location).attr('href', '../resources')
        });

        request.fail(function (jqXHR, msg) {
            console.log("Request failed: " + msg);
        });

    },

    getInitialState() {
        return {name: ''};
    },
    render() {
        return (
            <div>
                <NavMain activePage="resources"/>
                <PageHeader title="Resources" subTitle=""/>
                <Grid>
                    <Row>
                        <Input type="text" label="Resource Name"
                               valueLink={this.linkState('name')}
                               placeholder="Enter name of the resource that you prefer"/>
                    </Row>

                    <Row>
                        <ResourceType ref="resourceType"/>
                    </Row>

                    <Row>
                        <Col xsOffset={11}>
                            <Button bsStyle="primary" onClick={this.save}>Save</Button>
                        </Col>
                    </Row>

                </Grid>
                <PageFooter />
            </div>
        );
    }
});

export default Resource;
