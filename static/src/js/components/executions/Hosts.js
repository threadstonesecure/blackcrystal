import React from 'react';
import { Input, Row, Col, Grid, Table } from 'react-bootstrap';
import $ from 'jquery';
import elasticsearch from 'elasticsearch';
import { elasticSearchHost } from '../../utils/Config';



const Hosts = React.createClass({
    getClient(){
        return new elasticsearch.Client(elasticSearchHost());
    },

    getData(){
        var client = this.getClient();
        client.search({
            index: 'ansible_logs-*',
            type: 'ansible-stats',
            body: {
                "query": {
                    "bool": {
                        "must": [
                            {"match": {"executionId": this.props.executionId }},
                            {"match": {"jobName": this.props.jobName }}

                        ]
                    }
                }
            }
        }).then(function (resp) {
            this.setState({hosts: resp.hits.hits});
        }.bind(this), function (err) {
            console.trace(err.message);
        });

    },

    getInitialState() {
        return { hosts: []};
    },

    componentDidMount(){
        this.getData();
    },
    render() {
        return (
            <div>

                        <Table striped bordered condensed hover>
                            <thead>
                            <tr>
                                <th>Hosts</th>
                                <th>Ok</th>
                                <th>Changed</th>
                                <th>Failed</th>
                                <th>Unreachable</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.hosts.map(function (row) {
                                    return <tr>
                                        <td>{row._source.hostname}</td>
                                        <td>{row._source.ok}</td>
                                        <td>{row._source.changed}</td>
                                        <td>{row._source.failed}</td>
                                        <td>{row._source.unreachable}</td>
                                    </tr>
                                })}
                            </tbody>
                        </Table>

            </div>
        );
    }
});

export default Hosts;
