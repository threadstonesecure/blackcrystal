import React from 'react';
import {Input, MenuItem} from 'react-bootstrap';
import $ from 'jquery';
import ResourceTypeDirectory from './ResourceTypeDirectory'
import ResourceTypeGit from './ResourceTypeGit'
import { resourcesTypesURI } from '../../utils/Config'

const ResourceType = React.createClass({
  collect() {
    var resource = this.refs.resourceType.state;
    resource.type = this.state.repoType;
    return resource;
  },
  isValid() {
    //TODO : verify inputs are correct!
    return true;
  },
  onChange(event) {
    this.setState({repoType: event.target.value});
  },

  loadData() {
    return $.getJSON(resourcesTypesURI());
  },
  getInitialState() {
    return {data: [], repoType: this.props.selected};
  },
  componentWillReceiveProps: function (newProps) {
    this.setState({repoType: newProps.selected});
  },
  componentDidMount(){
    this.loadData().success(function (data) {
      if (this.isMounted()) {
        this.setState({data: data});
      }
    }.bind(this))
  },
  render() {

    var resourceTypeUI;
    if (this.state.repoType == 'directory') {
      resourceTypeUI = <ResourceTypeDirectory ref="resourceType" resource={this.props.resource}/>
    } else if (this.state.repoType == 'git') {
      resourceTypeUI = <ResourceTypeGit ref="resourceType" resource={this.props.resource}/>
    } else {
      resourceTypeUI = <div></div>
    }


    return (
      <div>
        <Input type="select" label="Resource Type" placeholder="Resource Type" onChange={this.onChange}>
          <option value="" disabled selected hidden>Please Choose a Resource Type</option>
          {
            this.state.data.map(function (item) {
                return <option value={item} >{item}</option>
              }
            )}

        </Input>
        { resourceTypeUI }
      </div>
    );
  }
});

export default ResourceType;
