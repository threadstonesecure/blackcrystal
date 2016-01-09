import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { jobExecutionURI, jobExecutionsURI } from '../utils/Config'

import { Link } from 'react-router';
import { Row, Col, Grid, Glyphicon, Pagination } from 'react-bootstrap';
import $ from 'jquery';

var View1Table = React.createClass({
    render: function () {
        return (
            <table className="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>ExecutionID</th>
                    <th>Start Time</th>
                    <th>Exit Code</th>
                    <th>Duration</th>

                    <th></th>

                </tr>
                </thead>
                <tbody>
                {
                    this.props.data.map(function (row) {
                        return <View1Row key={row.executionId} data={row}/>
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
                <td>{this.props.data.executionId}</td>
                <td>{this.props.data.startTime}</td>
                <td>{this.props.data.result}</td>
                <td>{this.props.data.duration}</td>
                <td>
                    <Link to={"/job/" + this.props.data.jobName+"/execution/"+this.props.data.executionId}>
                        <Glyphicon glyph="tasks"/>
                        Details
                    </Link>
                </td>
            </tr>
        );
    }
});

const pageSize = 20;

const Executions = React.createClass({
    loadData(page) {
        var request = $.getJSON(jobExecutionsURI(this.props.params.name, pageSize, page));
        request.success(function (data) {
            if (this.isMounted()) {
                this.setState(data);
            }
        }.bind(this))
    },
    getInitialState() {
        return {content: [], totalPages: 0, totalElements: 0, currentPage: 0, jobName: '', activePage: 1};
    },
    handleSelect(event, selectedEvent) {
        this.setState({activePage: selectedEvent.eventKey});
        this.loadData(selectedEvent.eventKey - 1);

    },
    componentDidMount(){
        this.loadData(0);
    },
    render() {
        return (
            <div>
                <NavMain activePage="jobs"/>
                <PageHeader title={this.props.params.name}
                            subTitle="Executions"/>

                <Grid>

                    <Row>
                        <Col>
                            <View1Table data={this.state.content}/>
                        </Col>
                    </Row>

                    <Row>
                        <Pagination
                            prev
                            next
                            first
                            last
                            ellipsis
                            items={this.state.totalPages}
                            maxButtons={15}
                            activePage={this.state.activePage}
                            onSelect={this.handleSelect}/>
                    </Row>

                </Grid>

                <PageFooter />
            </div>
        );
    }
});

export default Executions;
