import React from 'react';

import NavMain from './../navigation/NavMain';
import PageHeader from './../PageHeader';
import PageFooter from './../PageFooter';
import ResourceSelector  from './../resources/ResourceSelector';
import { Input, Row, Col, Grid, Table, Button } from 'react-bootstrap';
import $ from 'jquery';
import elasticsearch from 'elasticsearch';


const Overview = React.createClass({
    getClient(){
        return new elasticsearch.Client({host: 'localhost:9200', log: 'trace'});
    },

    getData(){
        var client = this.getClient();
        client.search({
            index: 'ansible_logs-*',
            type: 'ansible-runs',
            body: {
                "size": 0,
                "aggs": {
                    "hosts": {
                        "terms": {"field": "hostname"}
                    }
                }
            }
        }).then(function (resp) {
            var hits = resp.hits.hits;
            console.log(resp.aggregations.hosts.buckets);
            this.setState({hosts: resp.aggregations.hosts.buckets});
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
                <NavMain activePage="overview"/>
                <PageHeader title={this.props.params.name}/>
                <Grid>
                    <Row>
                        <Table striped bordered condensed hover>
                            <thead>
                            <tr>
                                <th>Hosts</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.hosts.map(function (row) {
                                    return <tr>
                                        <td>{row.key}</td>
                                    </tr>
                                })}
                            </tbody>
                        </Table>
                    </Row>
                </Grid>
                <PageFooter />
            </div>
        );
    }
});

export default Overview;
