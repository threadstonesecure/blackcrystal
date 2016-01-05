import React from 'react';
import {Input, MenuItem} from 'react-bootstrap';
import $ from 'jquery';
import ResourceTypeDirectory from './ResourceTypeDirectory'
import ResourceTypeGit from './ResourceTypeGit'

const ResourceType = React.createClass({
    collect() {
        var resource = this.refs.resourceType.state;
        resource.type = this.state.repoType;
        return  resource;
    },
    isValid() {
        //TODO : verify inputs are correct!
        return true;
    },
    onChange(event) {
        this.setState({repoType: event.target.value});
    },

    loadData() {
        return $.getJSON("http://localhost:8080/resource-types");
    },
    getInitialState() {
        return {data: []};
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
            resourceTypeUI = <ResourceTypeDirectory ref="resourceType" />
        } else  if (this.state.repoType == 'git') {
            resourceTypeUI = <ResourceTypeGit ref="resourceType" />
        } else {
            resourceTypeUI = <div></div>
        }

        return (
            <div>
                <Input type="select" label="Resource Type" placeholder="Resource Type" onChange={this.onChange}>
                    <option value="" disabled selected hidden>Please Choose a Resource Type</option>
                    {
                        this.state.data.map(function (item) {
                                return <option value={item}>{item}</option>
                            }
                        )}

                </Input>

                { resourceTypeUI }
            </div>
        );
    }
});

export default ResourceType;
