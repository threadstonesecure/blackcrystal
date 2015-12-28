import React from 'react';

import NavMain from './../navigation/NavMain';
import PageHeader from './../PageHeader';
import PageFooter from './../PageFooter';
import ResourceSelector  from './../resources/ResourceSelector';
import { Input, Row, Col, Grid, Button } from 'react-bootstrap';
import LinkedStateMixin from 'react-addons-linked-state-mixin';

import $ from 'jquery';


const JobDetails = React.createClass({
    mixins: [LinkedStateMixin],

    save(event){
        var jobConfig = this.state;
        jobConfig.resourceName = this.refs.resourceSelector.state.selected;
        this.put(jobConfig);
    },

    put(data) {
        var request = $.ajax({
            type: "PUT",
            url: "http://localhost:8080/job",
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: "json"
        });

        request.done(function (msg) {
            //TODO use react-router transitionTo
            $(location).attr('href', '../jobs')
        });

        request.fail(function (jqXHR, msg) {
            console.log("Request failed: " + msg);
        });

    },

    loadData() {
        return $.getJSON("http://localhost:8080/job/" + this.props.params.name);
    },

    getInitialState() {
        return {data: []};
    },

    componentDidMount(){
        this.loadData().success(function (data) {
            if (this.isMounted()) {
                this.setState(data);
            }
        }.bind(this))
    },

    render() {
        return (
            <div>
                <NavMain activePage="jobs"/>
                <PageHeader title={this.props.params.name}/>
                <Grid>
                    <Row>
                        <Input type="text" valueLink={this.linkState('name')}
                               placeholder="Name of the job"
                               label="Name of the job"/>
                    </Row>

                    <Row>
                        <Input type="checkbox" checkedLink={this.linkState('enabled')}
                               label="Enabled"/>
                    </Row>

                    <Row>
                        <Input type="text"
                               valueLink={this.linkState('executionDirectory')}
                               placeholder="Execution Directory"
                               label="Execution Directory"/>
                    </Row>

                    <Row>
                        <ResourceSelector ref="resourceSelector" selected={this.state.resourceName}/>

                    </Row>

                    <Row>
                        <Input type="text"
                               valueLink={this.linkState('executionTime')}
                               placeholder="Execution Time"
                               label="Execution Time"/>
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

export default JobDetails;
