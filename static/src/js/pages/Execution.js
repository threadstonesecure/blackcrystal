import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import ResourceSelector  from './../components/resources/ResourceSelector';
import { Input, Row, Col, Grid, Label,Glyphicon,ProgressBar,  Button } from 'react-bootstrap';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import ConsoleOutput from '../components/executions/ConsoleOutput';
import ExitCode from '../components/executions/ExitCode';
import juration from 'juration';
import Status from '../components/executions/Status';
import Hosts from '../components/executions/Hosts';
import Plays from '../components/executions/Plays';

import $ from 'jquery';


const Execution = React.createClass({
    mixins: [LinkedStateMixin],
    humanize(duration){
        if (duration == 0) {
            duration = 1;
        }
        return juration.stringify(duration, {format: 'long'});
    },
    loadData() {
        return $.getJSON("http://localhost:8080/job/" + this.props.params.name + "/execution/" + this.props.params.id);
    },
    getInitialState() {
        return {startTime: '', endTime: '', duration: '0', result: '0'};
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
                <PageHeader title={this.props.params.name}
                            subTitle={"Execution ID   "+this.props.params.id}/>
                <Grid>
                    <Row>
                        <ProgressBar>
                            <ProgressBar bsStyle="info" label={"Start Time : "+this.state.startTime} now={50} key={1}/>
                            <ProgressBar label={"End Time : "+this.state.startTime} now={50} key={2}/>
                        </ProgressBar>
                    </Row>

                    <Row>
                        <h2><Glyphicon bsSize="large" glyph="time"/>
                            It took {this.humanize(this.state.duration)}
                        </h2>
                    </Row>

                    <Row>
                        <ExitCode result={this.state.result}/>
                    </Row>

                    <Row>
                        <Col  xs={6}> <Status jobName={this.props.params.name} executionId={this.props.params.id}/></Col>
                        <Col  xs={6} > <Hosts jobName={this.props.params.name} executionId={this.props.params.id}/></Col>

                    </Row>

                    <Row>
                        <Col  xs={6}> <Plays jobName={this.props.params.name} executionId={this.props.params.id}/></Col>
                        <Col  xs={6} > </Col>

                    </Row>

                    <Row>
                        <ConsoleOutput jobName={this.props.params.name} executionId={this.props.params.id}/>
                    </Row>

                </Grid>
                <PageFooter />
            </div>
        );
    }
});

export default Execution;
