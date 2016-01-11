import React from 'react';

import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { jobExecutionURI, jobExecutionsURI } from '../utils/Config'
import moment from 'moment';
import { Link } from 'react-router';
import { Row, Col, Grid, Pagination } from 'react-bootstrap';
import $ from 'jquery';

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
        <PageHeader title={this.props.params.name} />
        <Grid>
          <Row>
            <Col>
              <div className="box box-success">
                <div className="box-header with-border">
                  <h3 className="box-title">Executions list</h3>
                </div>

                <div className="box-body">
                  <table className="table table-striped">
                    <tbody>
                    <tr>
                      <th>Execution ID</th>
                      <th>Start Time</th>
                      <th>Exit Code</th>
                      <th>Duration</th>
                      <th></th>
                    </tr>
                    {
                      this.state.content.map(function (row, i) {
                        return <tr>

                          <td>{row.executionId}</td>
                          <td> {moment(row.startTime).format('MMMM Do YYYY, h:mm a')}
                          </td>
                          <td>
                            {row.result}
                          </td>

                          <td>
                            {row.duration}
                          </td>

                          <td>
                            <Link to={"/job/" +row.jobName+"/execution/"+row.executionId}>
                              <button type="button" className="btn bg-olive btn-flat ion-ios-list-outline"> Details
                              </button>
                            </Link>
                          </td>
                        </tr>
                      })}
                    </tbody>
                  </table>
                </div>
              </div>
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
