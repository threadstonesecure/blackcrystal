import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import ResourceSelector  from './../components/resources/ResourceSelector';
import { Row, Col, Grid  } from 'react-bootstrap';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import ConsoleOutput from '../components/executions/ConsoleOutput';
import ExitCode from '../components/executions/ExitCode';
import Status from '../components/executions/Status';
import Hosts from '../components/executions/Hosts';
import Plays from '../components/executions/Plays';
import Tasks from '../components/executions/Tasks';
import Duration from '../components/executions/Duration';
import StartTime from '../components/executions/StartTime';
import TotalTasks from '../components/executions/TotalTasks';
import ExecutedCommand from '../components/executions/ExecutedCommand';
import { jobExecutionURI } from '../utils/Config';

import $ from 'jquery';


const Execution = React.createClass({
    mixins: [LinkedStateMixin],
    loadData() {
        return $.getJSON(jobExecutionURI(this.props.params.name, this.props.params.id)) ;
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
                        <Col><StartTime time={this.state.startTime} /></Col>
                        <Col><Duration duration={this.state.duration} /></Col>
                        <Col><ExitCode result={this.state.result}/></Col>
                        <Col><TotalTasks result={0}/></Col>
                     </Row>

                    <Row>
                        <ExecutedCommand jobName={this.props.params.name} executionId={this.props.params.id}/>
                    </Row>
    
                    <Row>
                        <Col  xs={6}> <Status jobName={this.props.params.name} executionId={this.props.params.id}/></Col>
                        <Col  xs={6} ><Hosts jobName={this.props.params.name} executionId={this.props.params.id}/></Col>
                    </Row>

                    <Row>
                        <Col  xs={6}> <Plays jobName={this.props.params.name} executionId={this.props.params.id}/></Col>
                        <Col  xs={6} ><Tasks jobName={this.props.params.name} executionId={this.props.params.id}/> </Col>
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
